# UI Modernization Strategy Notes
Date: 2026-02-27
Agent: Warp Agent (Claude Opus 4.6 max)

## Context
The IW_Launcher web portal currently uses JSP (JavaServer Pages) for all UI rendering. These JSPs run server-side inside Tomcat 9, directly calling Java objects like ConfigContext to build HTML. The user asked whether React/TypeScript could replace the UI while keeping the existing Java backend.

## Current Architecture
```
Browser → HTTP Request → Tomcat
  → JSP compiled to Servlet → calls ConfigContext (same JVM) → HTML response
  → Form POST → Servlet (e.g., ProductDemoServlet) → redirect → JSP
```

Key JSP pages:
- IMConfig.jsp — frameset wrapper
- BDConfigurator.jsp — user flows management (iterates ConfigContext.getTransactionList())
- BDConfiguratorA.jsp — admin flows management (DB config, flow assignment, save/restore)
- CompanyConfiguration.jsp — wizard pages
- IWLogin.jsp — login form

All JSPs directly access static Java methods (ConfigContext.getTransactionList(), ConfigContext.isHosted(), etc.) because they run in the same JVM as the business logic.

## Three Approaches Evaluated

### Approach 1: Full React SPA Rewrite
- Replace all JSPs with a standalone React application
- Requires ~10-15 new API servlets that return JSON
- React app served as static files by Tomcat (or separate server)
- **Effort:** High
- **Risk:** High — breaking changes during transition
- **Long-term value:** Highest

### Approach 2: React Hooks into Existing JSPs
- Embed React components inside existing JSP pages via `<div>` mount points
- JSPs pass data to React via `window.__DATA__` inline JavaScript
- No API servlets needed for initial page load
- **Effort:** Low
- **Risk:** Minimal — remove script tag to rollback
- **Long-term value:** Medium (hybrid complexity grows over time)

### Approach 3: Full React SPA via Phased Migration (RECOMMENDED)
Start with hooks, graduate to full SPA:
- Phase 1: Hook React into existing JSPs (quick wins, prove concept)
- Phase 2: Extract to standalone React app with routing, add API servlets
- Phase 3: React becomes primary UI, JSPs become fallback
- Phase 4: Remove JSPs entirely

## Enterprise Recommendation: Phased Migration to Full React SPA

### Why Full SPA Wins Long-Term
1. **Team scalability** — Frontend (React/TS) and backend (Java) developers work independently with clear API contract
2. **Testability** — Frontend unit tests don't need Tomcat running; backend API tests don't need a browser
3. **Deployment flexibility** — Static React bundle can serve from Tomcat, CDN, Nginx, or any static host
4. **Security** — Formal API boundary forces explicit auth, input validation, rate limiting
5. **Hiring** — React/TypeScript developers are abundant; JSP developers barely exist in 2026
6. **Extensibility** — Same JSON API serves mobile apps, CLI tools, and third-party integrations for free

### Why Start With Hooks (Phase 1)
- Delivers visible UI improvement immediately
- No infrastructure changes needed
- Proves React works with the existing Java stack
- De-risks the larger migration
- Each hook naturally becomes a component in the eventual SPA

## What React Enables (Beyond Current JSPs)
- **Live dashboard** — flow states update without page reload (polling or SSE)
- **Inline editing** — change intervals, start/stop flows without form submission
- **Real-time log streaming** — tail logs in-page via Server-Sent Events
- **Toast notifications** — success/error feedback without navigation
- **Search/filter** — instantly filter flows by name, state, profile
- **Charts/graphs** — success/failure trends, execution duration over time
- **Drag-and-drop** — reorder or assign flows
- **Responsive design** — works on mobile/tablet

## API Endpoints Needed (For Phases 2+)
| Endpoint | Method | Purpose |
|----------|--------|---------|
| /api/flows/status | GET | Current flow states (for live polling) |
| /api/flows/submit | POST | Start/stop/update flows (replaces ProductDemoServlet form) |
| /api/config | GET | Read saved configuration |
| /api/config/save | POST | Save configuration changes |
| /api/logs/stream | GET (SSE) | Real-time log streaming |
| /api/auth/login | POST | Authentication (replaces LoginServlet form) |
| /api/auth/session | GET | Check current session status |
| /api/users | GET | List users (admin) |
| /api/flows/assign | POST | Flow assignment (admin) |
| /api/queries/execute | POST | Execute a query |

## Technical Notes

### JSP Data Injection (Phase 1)
```jsp
<script>
  window.__FLOWS__ = [
    <% for(int i = 0; i < ConfigContext.getTransactionList().size(); i++) {
        TransactionContext tc = ConfigContext.getTransactionList().get(i);
        if(i > 0) out.print(","); %>
        { "id": "<%= tc.getTransactionId() %>" }
    <% } %>
  ];
</script>
<div id="flows-root"></div>
<script src="static/react-bundle.js"></script>
```

### API Servlet Pattern (Phase 2+)
```java
public class ApiFlowsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        // Read from existing ConfigContext — no business logic changes
        Vector<TransactionContext> list = ConfigContext.getTransactionList();
        // Serialize to JSON and write response
    }
}
```

### Session Management
- Phase 1: No change — JSPs handle auth via existing HttpSession
- Phase 2+: HttpSession cookies still work since React and API are on same origin (Tomcat)
- Phase 4: Consider JWT tokens if API will serve external clients

## Build Toolchain Addition
- React project lives at: `web_portal/tomcat/webapps/iw-business-daemon/static/` (or similar)
- Build tool: Vite (fast, TypeScript-native, outputs single bundle)
- Output: `react-bundle.js` + `react-bundle.css`
- No impact on existing Java compilation workflow
- npm/node only needed for frontend development, not for deployment

## Decision Log
| Date | Decision | Rationale |
|------|----------|-----------|
| 2026-02-27 | Evaluate React modernization | Current JSP UI limits interactivity and developer hiring |
| 2026-02-27 | Phased approach recommended | Balances immediate value with long-term architecture |
| 2026-02-27 | Phase 1 = React hooks in JSPs | Lowest risk, fastest delivery, proves concept |
| 2026-02-27 | Long-term target = Full React SPA | Enterprise scalability, testability, extensibility |
