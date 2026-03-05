# Contribution Guide

Thank you for your interest in contributing to this project!

To ensure code quality and consistency, please follow the guidelines below.

## 1. Workflow

We use a **Simplified Gitflow** workflow:

1. **Fork** the repository.  
2. Create a new **branch** from `main` for your feature or fix:  
   ```bash
   git checkout -b feat/feature-name
   ```
3. Implement your changes.  
4. Make sure all **tests** pass and **code coverage** has not decreased.  
5. Follow the **Commit Convention** (see below).  
6. Open a **Pull Request (PR)** to the `main` branch.  

---

## 2. Code Standards

* **Java:** Follow the official Spring Boot coding standards and Java naming conventions.  
* **Tests:** Every new feature or bug fix must include relevant unit and/or integration tests.  
* **Documentation:** Update the `README.md` and Swagger documentation if any *endpoints* are changed.  

---

## 3. Commit Convention

We follow the **Conventional Commits** specification.  
The format should be:

```
<type>(<scope>): <description>
```

**Examples:**

* `feat(city): add state filter to city search`
* `fix(auth): fix authentication failure when token expires`
* `test(service): increase test coverage for ServiceCity`

---

## 4. Code Review

Your Pull Request will be reviewed by one of the maintainers.  
Be prepared to receive feedback and make necessary adjustments.
