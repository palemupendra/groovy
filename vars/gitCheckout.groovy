// vars/gitCheckout.groovy - Shared Library Function
def call(Map config) {
    def repoUrl = config.repoUrl ?: ''
    def branch = config.branch ?: 'main'
    def credentialsId = config.credentialsId ?: ''
    
    if (!repoUrl) {
        error "Repository URL is required"
    }
    
    echo "Checking out ${repoUrl} on branch ${branch}"
    
    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[
            url: repoUrl,
            credentialsId: credentialsId
        ]],
        extensions: [
            [$class: 'CleanBeforeCheckout'],
            [$class: 'CloneOption', depth: 1, shallow: true]
        ]
    ])
    
    echo "Git checkout completed successfully"
}