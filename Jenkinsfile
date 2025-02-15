pipeline {
    agent { docker {  
    image 'maven:3.9.5-amazoncorretto-17' // Use an image with Maven and Java 17  
    args '-v /root/.m2:/root/.m2' // Mount the Maven cache  
}  
    }
    environment {  
        SONAR_PROJECT_KEY = "your-project-key" // SonarQube 项目的 Key  
        SONAR_HOST_URL = "127.0.0.1:9001" // SonarQube 服务器的 URL  
        SONAR_TOKEN = "squ_66cf9dfb2feaa0f5ac8acf1d6c91241b7ca8af64" // SonarQube 项目的 Token  
    }  

    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
         stage('SonarQube Analysis') {  
            steps {  
                script {  
                    // 使用 SonarQube 的 Docker 镜像运行扫描  
                    docker.image('sonarqube:community').inside {  
                        sh """  
                            sonar-scanner \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.sources=. \
                            -Dsonar.host.url=${SONAR_HOST_URL} \
                            -Dsonar.login=${SONAR_TOKEN}  
                        """  
                    }  
                }  
            }  
        }  

        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    }
    post {  
        success {  
            echo 'SonarQube analysis completed successfully.'  
        }  
        failure {  
            echo 'SonarQube analysis failed.'  
        }  
    }  

}
