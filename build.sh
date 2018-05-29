docker stop estimation-backend
docker rm estimation-backend
docker rmi estimation-backend
mvn package docker:build
docker run -d -p 8011:8011 --name estimation-backend estimation-backend