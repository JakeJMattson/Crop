FROM gradle:8.14-jdk17 AS build
COPY --chown=gradle:gradle . /Crop
WORKDIR /Crop
RUN gradle shadowJar

FROM eclipse-temurin:17-jre
COPY --from=build /Crop/build/libs/crop.jar /

ENTRYPOINT ["java", "-jar", "/crop.jar"]