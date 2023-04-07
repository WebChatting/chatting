pipeline {
	agent {
		label 'fx50j-arch'
	}

	stages {
		stage('Clean Old Build') {
			steps {
				sh 'make clean'
			}
		}
		stage('Build') {
			steps {
				sh 'make build'
			}
		}
		stage('Deploy') {
			steps {
				// use "-v chatting-mysql:/var/lib/mysql" to save database
				sh 'docker-compose up -d'
			}
		}
	}
}
