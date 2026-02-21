@Library('my-groovy-lib') _

pipeline {
    agent any
    parameters {
        string(name: 'REPO_URL',    defaultValue: '', description: 'Git Repo URL')
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch Name')
    }
    stages {
        stage('Git Checkout') {
            steps {
                gitCheckout(
                    repoUrl      : params.REPO_URL,
                    branch       : params.BRANCH_NAME,
                    credentialsId: 'github-credentials'
                )
            }
        }
    }
}
```

**Where to place the file:**
```
(shared-library-repo)/
└── vars/
    └── gitCheckout.groovy   ← goes here