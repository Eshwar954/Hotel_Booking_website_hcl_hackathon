/** @type {import('tailwindcss').Config} */

export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],

  darkMode: "class",

  theme: {
    extend: {
      colors: {
        primary: "#002045",
        secondary: "#546066",
        background: "#faf9fd",
        surface: "#ffffff",
        accent: "#d4903b",
      },

      borderRadius: {
        lg: "0.5rem",
        xl: "0.75rem",
        full: "9999px",
      },

      fontFamily: {
        sans: ["Inter", "sans-serif"],
        heading: ["Montserrat", "sans-serif"],
      },
    },
  },

  plugins: [],
}