.PHONY: help check test coverage bench all

.DEFAULT_GOAL := help

CYAN := \033[36m
GREEN := \033[32m
RESET := \033[0m

help: ## Show this help
	@grep -E '^[0-9a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
	| awk 'BEGIN {FS = ":.*?## "}; {printf "$(CYAN)%-30s$(RESET) %s\n", $$1, $$2}'

check: ## Check statically
	@echo -e "$(GREEN)Checking statically$(RESET)"
	-lein eastwood
	-clj-kondo --lint src test

test: ## Run tests
	@echo -e "$(GREEN)Running tests$(RESET)"
	@lein test

coverage: ## Estimate coverage
	@echo -e "$(GREEN)Estimating coverage$(RESET)"
	@lein with-profile coverage cloverage --codecov

bench: ## Don't guess, just benchmark
	@echo -e "$(GREEN)Benchmarking$(RESET)"
	@find bench -name '*.clj' \
		-exec echo \; -exec echo {} \; -exec lein with-profile bench exec -p {} \;

all: check test coverage bench ## Do check, test, coverage, and bench
