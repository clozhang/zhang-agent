kibit:
	@lein with-profile +testing kibit

bikeshed:
	@lein with-profile +testing bikeshed

base-eastwood:
	@lein with-profile +testing eastwood "$(EW_OPTS)"

yagni:
	@lein with-profile +testing yagni

eastwood:
	@EW_OPTS="{:namespaces [:source-paths]}" make base-eastwood

lint: kibit eastwood

lint-all: lint bikeshed yagni

lint-unused:
	@EW_OPTS="{:linters [:unused-fn-args :unused-locals :unused-namespaces :unused-private-vars :wrong-ns-form] :namespaces [:source-paths]}" make base-eastwood

lint-ns:
	@EW_OPTS="{:linters [:unused-namespaces :wrong-ns-form] :namespaces [:source-paths]}" make base-eastwood

check: local-standalone lint
	@lein with-profile +testing test

ancient:
	@lein with-profile +testing ancient check.check-profiles
