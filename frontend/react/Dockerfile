#From where you want to pull node
FROM node:20-alpine3.19
#Accepeted Arg that is used to change the host port variable for docker-compose.yml
ARG api_base_url
#Set a working directory where to put all generated files
WORKDIR /app
#Copy package.json and package-lock.json into working directory (set with .)
#Note first directory of copy is relative to the src, second is working directory
COPY package*.json .
#Installing npm in work directory
RUN npm i --silent
#Copy all react files into working directory, except ignored files in .dockerignore
#Note first directory of copy is relative to the react folder, second is working directory
COPY . .
#Make sure "VITE_API_BASE_URL" from .env is set to same port as the one in docker-compose.yml
#With > you overwrite the .env file with new line
#Since .env is ignored, it will create a new file called .env in working directory
RUN echo "VITE_API_BASE_URL=${api_base_url}" > .env
#Expose the port we are using our react app it
EXPOSE 5173
#And then run our react app
CMD ["npm", "run", "dev"]