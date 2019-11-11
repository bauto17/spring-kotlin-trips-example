FROM openjdk:11

RUN env
RUN mkdir /code
WORKDIR "/code"
COPY . ./
RUN ./gradlew build -x test

ENTRYPOINT [ "sh", "-c", "java -jar -Duser.timezone=$TIMEZONE -Dnetworkaddress.cache.ttl=60 -Dnetworkaddress.cache.negative.ttl=30 -Xms512m -Xmx2048m -server -XX:+UseG1GC -XX:MaxGCPauseMillis=200 /code/build/libs/*.jar" ]