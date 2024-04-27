/** @type {import('next').NextConfig} */
const nextConfig = {
    output: 'export',
    assetPrefix: ".",
    reactStrictMode: false,
    webpack: (config) => {
    config.resolve.fallback = {
      fs: false,
      net: false,
      dns: false,
      child_process: false,
      tls: false,
    };

    return config;
  },
}

module.exports = nextConfig
