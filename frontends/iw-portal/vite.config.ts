import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import path from "path";

const isVercel = !!process.env.VERCEL;
const backendProxy = process.env.VITE_DEV_PROXY_TARGET ?? "http://localhost:9090";

export default defineConfig({
  plugins: [react(), tailwindcss()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  server: {
    port: 5173,
    proxy: {
      "/iw-business-daemon": {
        target: backendProxy,
        changeOrigin: true,
      },
    },
  },
  build: {
    outDir: isVercel ? "dist" : "../../web_portal/tomcat/webapps/iw-portal",
    emptyOutDir: true,
    rollupOptions: {
      output: {
        manualChunks: {
          "radix-ui": [
            "@radix-ui/react-dialog",
            "@radix-ui/react-label",
            "@radix-ui/react-select",
            "@radix-ui/react-separator",
            "@radix-ui/react-slot",
            "@radix-ui/react-switch",
            "@radix-ui/react-tabs",
            "@radix-ui/react-tooltip",
          ],
        },
      },
    },
  },
  base: isVercel ? "/" : "/iw-portal/",
});
