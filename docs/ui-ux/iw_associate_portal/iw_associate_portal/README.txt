ASSA Customer Portal (Static HTML Prototype)

Entry points:
- login.html (mock login)
- index.html (portal home)

Serve locally:
  python -m http.server 8080
Open:
  http://localhost:8080/assa_customer_portal/login.html

Notes:
- Static UI prototype based on the Master Console information architecture.
- Wire to back-end services (Auth/MFA, CMS, Billing, Notifications) via Integration Hub.