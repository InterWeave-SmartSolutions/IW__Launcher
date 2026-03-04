# INTERWEAVE_PDF_CONTEXT.md

This file defines the approved legacy PDF corpus that AI agents may use as historical reference when working in `IW_Launcher/`.

Use this material to understand how InterWeave was documented, named, operated, and integrated. This context supplements the current codebase and current Markdown docs; it does not replace them.

## Rules

- Use these PDFs as supporting context for legacy terminology, workflows, UI labels, training steps, and integration expectations.
- Prefer current source code, current Markdown docs, and current runtime behavior when they conflict with a PDF.
- Apply the PDF corpus additively only. Preserve existing docs and behavior unless the user explicitly asks to replace or rename something.
- When a task is materially informed by these PDFs, note the relevant document names in `docs/ai/AI_WORKLOG.md`.
- Prefer the canonical paths below. Many mirrored copies exist under `frontends/InterWoven/docs/IW_Docs/**` and the parent directory; avoid rereading duplicates unless the canonical copy is missing.

## Canonical legacy manuals

Primary InterWeave references:

- `docs/legacy-pdfs/InterWeave_IDE_Binder.pdf`
  Broad legacy IDE reference binder for the original InterWeave development environment.
- `docs/legacy-pdfs/IDETutorial.pdf`
  Step-by-step IDE training material for creating and using integration projects.
- `docs/legacy-pdfs/IW_IDE_UR.pdf`
  Legacy IDE requirements and usage expectations. There are two distinct variants in the repository; if behavior is ambiguous, also check `frontends/InterWoven/docs/IW_Docs/IW_IDE/IW_IDE_Import/IW_IDE_UR.pdf`.
- `frontends/InterWoven/docs/IW_Docs/IW_IDE/IW_IDE_Import/IDE Quick Installation Guide _2_.pdf`
  Historical install prerequisites and setup expectations for the legacy IDE distribution.
- `docs/legacy-pdfs/InterWeave%20HelpandTraining.pdf`
  Solutions Portal help and training manual covering registration, configuration, management, and monitoring workflows.
- `web_portal/tomcat/webapps/docs/architecture/startup/serverStartup.pdf`
  Tomcat startup sequence diagram useful for understanding the embedded portal's legacy server architecture.
- `frontends/InterWoven/docs/IW_Docs/CDs/PTE_8.0/Protocol Translation Engine 8.pdf`
  Legacy Protocol Transformation Engine architecture and mapping concepts that explain the platform's transformation model.

Payment and partner integration references:

- `docs/legacy-pdfs/aim_guide.pdf`
  Authorize.Net AIM integration guide (historical).
- `docs/legacy-pdfs/CIM_SOAP_guide.pdf`
  Authorize.Net CIM SOAP guide (historical).
- `docs/legacy-pdfs/echeck.pdf`
  Authorize.Net eCheck AIM guide (historical).
- `docs/legacy-pdfs/developerGuide.pdf`
  Virtual Merchant developer guide (historical).
- `docs/legacy-pdfs/userGuide.pdf`
  Virtual Merchant user guide (historical operations reference).
- `docs/legacy-pdfs/IT Professional Services Agreement - First Universal 06_05_08.pdf`
  Historical project/client agreement that provides operational and ownership context for legacy integration work.

## How to apply this context

- Treat InterWeave domain terms in the PDFs as canonical legacy vocabulary unless the current code clearly uses different names.
- Preserve legacy concepts such as solution registration, company configuration, business daemon configuration, transaction flows, utility flows, and query-driven integrations when modernizing UI or documentation.
- Expect the legacy platform to assume users understand SQL, XML, XSLT, and XPath. Keep that in mind when writing developer-facing guidance.
- Use vendor PDFs to understand why legacy connectors, field names, or payload shapes exist. Do not treat those PDFs as current vendor API documentation for new integrations.
- If you create new documentation from this material, call out when a statement is based on historical PDF guidance versus current implementation.

## Duplicate and mirror guidance

- Shared legacy manuals are usually canonical under `docs/legacy-pdfs/`.
- Some manuals also exist under `frontends/InterWoven/docs/IW_Docs/**` and in `C:\\IW_IDE\\IW_CDs\\` or `C:\\IW_IDE\\IW_IDE_1.0\\`; these are usually mirrors.
- The `frontends/InterWoven/` application code remains out of scope unless the user explicitly requests it. This exception applies only to the mirrored documentation paths listed here.

## Quick interpretation notes

- Most of this corpus is historical (roughly 2004-2013, with some later vendor PDFs). Expect outdated Java, Tomcat, SOAP, and gateway assumptions.
- These PDFs are best for understanding legacy intent, user expectations, terminology, and connector history.
- For runtime truth, always verify against the current repository's scripts, source, database schema, and deployed web assets.
