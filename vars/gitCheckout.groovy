#!/usr/bin/env groovy

/**
 * gitCheckout.groovy
 * -------------------
 * Reusable Git Checkout shared library function.
 * Call this from any pipeline — no hardcoding needed.
 *
 * Usage:
 *   gitCheckout(
 *       repoUrl     : 'https://github.com/org/repo.git',
 *       branch      : 'main',
 *       credentialsId: 'github-creds'
 *   )
 */

def call(Map config = [:]) {

    // ── Validate Required Inputs ──────────────────────────────────────
    if (!config.repoUrl?.trim()) {
        error("❌ [gitCheckout] 'repoUrl' is required but was not provided.")
    }

    if (!config.repoUrl.startsWith("https://")) {
        error("❌ [gitCheckout] 'repoUrl' must start with 'https://'. Got: ${config.repoUrl}")
    }

    if (!config.branch?.trim()) {
        error("❌ [gitCheckout] 'branch' is required but was not provided.")
    }

    if (!config.credentialsId?.trim()) {
        error("❌ [gitCheckout] 'credentialsId' is required but was not provided.")
    }

    // ── Perform Checkout ──────────────────────────────────────────────
    echo "🚀 [gitCheckout] Checking out '${config.branch}' from '${config.repoUrl}'..."

    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${config.branch}"]],
        userRemoteConfigs: [[
            url          : config.repoUrl,
            credentialsId: config.credentialsId
        ]],
        extensions: [
            [$class: 'CleanBeforeCheckout'],
            [$class: 'CloneOption', depth: 1, shallow: true, noTags: false],
            [$class: 'CheckoutOption', timeout: 10]
        ]
    ])

    // ── Verify & Log ──────────────────────────────────────────────────
    def commitHash = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    def commitMsg  = sh(script: "git log -1 --pretty=%s",     returnStdout: true).trim()
    def author     = sh(script: "git log -1 --pretty=%an",    returnStdout: true).trim()

    echo """
    ─────────────────────────────────────
    ✅ [gitCheckout] Checkout Successful
       Repo    : ${config.repoUrl}
       Branch  : ${config.branch}
       Commit  : ${commitHash}
       Message : ${commitMsg}
       Author  : ${author}
    ─────────────────────────────────────
    """
}