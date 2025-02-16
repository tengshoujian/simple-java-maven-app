pipeline {
    agent { docker {  
    image 'maven:3.9.5-amazoncorretto-17' // Use an image with Maven and Java 17  
    args '-v /root/.m2:/root/.m2' // Mount the Maven cache  
}  
    }
    environment {  
        SONAR_PROJECT_KEY = "my-app" // SonarQube 项目的 Key  
        SONAR_HOST_URL = "127.0.0.1:9000" // SonarQube 服务器的 URL  
        SONAR_TOKEN = "sqa_a375025dd9144fc9bb541b1a01f02c4173474732" // SonarQube 项目的 Token  
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
        stage('SonarQube Scan') {  
            steps {  
                script {  
                    docker.image('sonarsource/sonar-scanner-cli:latest').inside('-v /var/run/docker.sock:/var/run/docker.sock') {  
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
