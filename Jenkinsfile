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
		stage('Docker Build') {
			steps {
				sh 'make docker-build'
			}
		}
		stage('Deploy') {
			steps {
				sh 'make deploy'
			}
		}
	}
}

// vim: ft=groovy
