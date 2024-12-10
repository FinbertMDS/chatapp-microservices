FROM node:lts-alpine as builder

WORKDIR /app
COPY package.json .

RUN yarn

COPY . .
RUN yarn build

FROM nginx:alpine

COPY --from=builder /app/build /usr/share/nginx/html

# Copy the default nginx.conf provided by tiangolo/node-frontend
COPY nginx.conf /etc/nginx/conf.d/default.conf