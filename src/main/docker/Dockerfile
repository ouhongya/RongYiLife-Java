FROM java:8-jre-alpine

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo 'Asia/Shanghai' >/etc/timezone

WORKDIR /app
ENTRYPOINT java -jar /app/rysh*.jar


#ADD rysh*.jar app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/rysh*.jar"]