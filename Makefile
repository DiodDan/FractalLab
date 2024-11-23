lint:
	mvn checkstyle:check modernizer:modernizer spotbugs:check pmd:check pmd:cpd-check

test:
	mvn test

tree:
	mvn dependency:tree

clean_lint:
	mvn clean verify
	mvn checkstyle:check modernizer:modernizer spotbugs:check pmd:check pmd:cpd-check

wlint:
	mvn checkstyle:check modernizer:modernizer spotbugs:check pmd:check pmd:cpd-check | findstr "[ERROR]"
