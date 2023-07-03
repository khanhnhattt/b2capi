FROM maven:latest

# Create workdir
RUN mkdir "/khanhnhatt"
WORKDIR /khanhnhatt
COPY . .

EXPOSE 8080
CMD [ "mvn", "spring-boot:run" ]
