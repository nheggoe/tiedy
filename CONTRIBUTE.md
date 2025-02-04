# Contributing to This Repository

### Branching Workflow

1. Main Branch
    * The main branch is protected:
    * Direct commits are not allowed.
    * All changes must go through a Pull Request (PR) with reviews and checks. (Merge Request on GitLab)

2. Creating a New Issue
    * Before starting work, create an issue to document:
    * The problem or feature request.
    * Expected outcomes.
    * Any relevant details or requirements.

### Development Workflow

1. Create a Branch
    * Using the GitLab default branch naming scheme is sufficient.

2. Open a Draft Pull Request
    * Once the branch is created, open a Draft PR:
    * This allows others to track progress and provide early feedback.
    * Draft PRs do not require reviews or checks initially.

3. Convert to Active PR
    * When the work is complete:
    * Convert the draft PR into an active PR.
    * Ensure your code is clean and adheres to the repository’s standards.
    * Test your changes locally and verify all CI/CD checks pass.

### Pull Request Guidelines

1. PR Content
    * Clearly link the PR to the corresponding issue.
    * Provide a concise description of:
       * What the PR does.
       * Any testing performed.
       * Any impact or dependencies.

2. Code Review
    * Be open to feedback from reviewers.
    * Address comments promptly and update the PR with small, descriptive commits.

3. Approvals and Merging
    * Ensure the following before merging:
    * At least one review approval (or more, if required).
    * All checks (tests, linters(SonarQube), etc.) have passed.
    * Once approved, squash and merge the PR into main.
    * Delete the feature branch after merging.

### Automation and Tools

1. CI/CD Checks
    * The repository is configured to run automated tests and checks on every PR.
    * Ensure that your code passes all checks before requesting a review.

2. Branch Protection Rules
    * The following rules are enforced on the main branch:
    * Required status checks must pass before merging.
    * PR reviews are mandatory.
    * Force-pushes are disabled.

### General Guidelines

1. Code Quality: Follow the project’s coding standards.
2. Testing: Write tests for new features or bug fixes.
3. Respect: Be kind and constructive in all discussions and reviews.