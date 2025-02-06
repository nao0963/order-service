FROM gradle:8.11.1-jdk17 as build
#소스코드를 복사할 작업디렉토리를 생성
WORKDIR /myapp
#호스트머신에 소스코드를 이미지작업 디렉토리로 복사
COPY . /myapp
#gradle의 종속성 먼저 복사해서 캐싱
COPY gradle /myapp/gradle
COPY gradlew /myapp/
COPY build.gradle settings.gradle /myapp/
#이전 빌드에서 생성된 모든 build/디렉토리 내용을 삭제, 새롭게 빌드
#프로젝트를 빌드(jar file)
#--no-daemon은 데몬을 이용하지 않고 빌드
#gradle은 설치되어 있는 gradle을 이용해서 빌드, gradlew는 프로젝트에 포함된 gradle을 이용
#CICD에서는 gradlew를 이용해서 작업
#-x test -> test를 제외하고 작업
#실행권한 추가
RUN chmod +x gradlew
#gradel종속성 먼저 다운로드
RUN ./gradlew dependencies --no-daemon
RUN ./gradlew clean build --no-daemon -x test

FROM openjdk:17-alpine
WORKDIR /myapp
COPY --from=build /myapp/build/libs/*.jar /myapp/orderservice.jar
EXPOSE 9200
ENTRYPOINT ["java","-jar","orderservice.jar"]
