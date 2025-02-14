pipeline {
    agent { docker {  
    image 'maven:3.9.5-amazoncorretto-17' // Use an image with Maven and Java 17  
    args '-v /root/.m2:/root/.m2' // Mount the Maven cache  
}  
    }
environment {  
        SONAR_SCANNER_HOME = tool name: 'SonarQubeScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'  
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
                withSonarQubeEnv('sonarqube') { // 'SonarQube' 是你在系统设置中配置的 SonarQube 服务器名称  
                    sh "${SONAR_SCANNER_HOME}/bin/sonar-scanner -Dsonar.projectKey=mytest -Dsonar.sources=. -Dsonar.host.url=http://127.0.0.1:9001 -Dsonar.login=squ_0a9046966ca12e43d62092653a18207631bac249"  
                }  
            }  
        }  
      
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    }
}
