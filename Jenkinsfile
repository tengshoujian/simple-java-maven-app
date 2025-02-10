pipeline {
    agent { docker {  
    image 'maven:3.9.5-amazoncorretto-17' // Use an image with Maven and Java 17  
    args '-v /root/.m2:/root/.m2' // Mount the Maven cache  
}  
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
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    }
}
