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
				sh 'docker run -d -p 3333:3333 -p 8088:8088 --name chatting --restart unless-stopped webchatting/chatting'
			}
		}
	}
}
