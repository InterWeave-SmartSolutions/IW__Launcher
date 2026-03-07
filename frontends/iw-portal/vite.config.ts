import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import path from "path";

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
        target: "http://localhost:9090",
        changeOrigin: true,
      },
    },
  },
  build: {
    outDir: "../../web_portal/tomcat/webapps/iw-portal",
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
  base: "/iw-portal/",
});
