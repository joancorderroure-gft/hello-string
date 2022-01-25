FROM openjdk:11
wORKDIR /opt/hello-spring
COPY build/libs/demo-0.0.1-SNAPSHOT.jar ./
CMD java -jar demo-0.0.1-SNAPSHOT.jar