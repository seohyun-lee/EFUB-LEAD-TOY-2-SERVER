# 사용할 base 이미지 선택
# ec2 아키텍처가 arm64v8이라서 적절한 이미지 가져옴
FROM openjdk:17

# build/libs/ 에 있는 jar 파일을 JAR_FILE 변수에 저장
ARG JAR_FILE=build/libs/*.jar

# JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "app.jar"]
