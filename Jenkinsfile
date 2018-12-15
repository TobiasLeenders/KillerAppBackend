pipeline {
  agent any
  options {
    disableConcurrentBuilds()
    timeout(time: 10, unit: 'MINUTES')
  }
  stages {
    stage('Verify Tools') {
      steps {
        parallel (
          java: {
            sh 'java -version'
            sh 'which java'
          },
          maven: {
            sh 'mvn -version'
            sh 'which mvn'
          },
          docker: {
            sh 'docker --version'
            sh 'which docker'
          }
        )
      }
    }
    stage('Build') {
      steps {
        sh 'mvn clean install -B -DskipTests'
		sh 'mvn package'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
      //post {
      //  always {
      //    junit '**/target/surefire-reports/*.xml'
      //  }
      //}
    }
    stage('Deploy dockerbranch') {
           when {
             branch 'dockerbranch'
           }
           steps {
             sh 'docker build -t killerapp-backend .'
             sh 'docker rm -f killerapp-backend || true'
             sh 'docker run -d -p 9998:9998 --name killerapp-backend killerapp-backend'
             sh 'docker image prune -f'
           }
         }
  }
  post {
    always {
      cleanWs()
    }
  }
}