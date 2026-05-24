# Jenkins + Maven + GitHub Packages Integration Guide

## Objective

This guide explains how to:

- Build a Java WAR application using Maven
- Run Checkstyle, Unit Tests, and JaCoCo Coverage
- Publish artifacts to GitHub Packages
- Configure Jenkins credentials securely
- Use Managed Maven Settings instead of inline `settings.xml`

---

# Prerequisites

Ensure the following are available:

- Jenkins installed
- Java 21 configured in Jenkins
- Maven configured in Jenkins
- GitHub repository available
- GitHub Personal Access Token (PAT)

---

# Step 1 — Generate GitHub Personal Access Token

Open:

```text
https://github.com/settings/tokens
```

Create a token with these scopes:

```text
repo
write:packages
read:packages
```

Copy the generated token.

---

# Step 2 — Configure Jenkins Credentials

Open Jenkins:

```text
Manage Jenkins
→ Credentials
→ Global
→ Add Credentials
```

## Select

```text
Kind: Username with password
```

## Values

| Field | Value |
|---|---|
| Username | GitHub Username |
| Password | GitHub PAT Token |
| ID | github-packages |

Save the credentials.

---

# Step 3 — Install Required Jenkins Plugins

Open:

```text
Manage Jenkins
→ Plugins
→ Available Plugins
```

Install the following plugins:

## Required Plugins

- Pipeline Maven Integration Plugin
- Coverage Plugin
- Warnings Next Generation Plugin
- Pipeline Stage View Plugin (optional)
- Blue Ocean Plugin (optional)

---

# Step 4 — Configure Managed Maven Settings.xml

Open:

```text
Manage Jenkins
→ Managed Files
→ Add New Config
→ Maven settings.xml
```

## File ID

```text
maven-github-settings
```

## settings.xml Content

```xml
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>${env.GITHUB_USERNAME}</username>
            <password>${env.GITHUB_TOKEN}</password>
        </server>
    </servers>
</settings>
```

Save the file.

---

# Step 5 — Update pom.xml

Add the following block before:

```xml
<dependencies>
```

## distributionManagement Section

```xml
<!-- GitHub Packages Deployment -->
<distributionManagement>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>
            https://maven.pkg.github.com/lerndevops/it-services-portal
        </url>
    </repository>
</distributionManagement>
```

---

# Step 6 — Complete Jenkinsfile

```groovy
// Full Jenkinsfile content from setup
```

---

# Step 7 — Trigger Jenkins Build

Open Jenkins Job:

```text
Build Now
```

Pipeline stages executed:

```text
Checkout
→ Compile
→ Checkstyle
→ Unit Test
→ Code Coverage
→ Package
→ Deploy Artifact
```

---

# Step 8 — Verify GitHub Packages Upload

Open your GitHub repository.

Navigate to:

```text
Packages
```

You should see:

- WAR artifacts
- Version history
- Package metadata

---

# Common Issues

## 401 Unauthorized

Usually caused by:

- Wrong GitHub token
- Missing scopes
- Incorrect repository URL
- ID mismatch between settings.xml and pom.xml

Ensure:

```xml
<id>github</id>
```

matches everywhere.

---

# Final Architecture

```text
GitHub Repository
        ↓
Jenkins Pipeline
        ↓
Maven Build
        ↓
JaCoCo + Checkstyle + Unit Tests
        ↓
GitHub Packages
        ↓
WAR Artifact Storage
```
