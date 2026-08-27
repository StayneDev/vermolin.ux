# syntax=docker/dockerfile:1

# ---- build ----
FROM node:20-alpine AS build
WORKDIR /build
COPY package.json package-lock.json ./
RUN npm ci
COPY . .
RUN npm run build -- --configuration production

# ---- runtime ----
FROM nginx:1.27-alpine AS runtime
COPY nginx.conf /etc/nginx/conf.d/default.conf
# outputPath declarado em angular.json: dist/vermolin-ux-frontend
COPY --from=build /build/dist/vermolin-ux-frontend /usr/share/nginx/html
EXPOSE 80
