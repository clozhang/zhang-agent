compile:
	@lein compile

show-versions:
	@echo Project: $(PROJ), $(PROJ_VERSION)
	@echo Erlang: $(ERL_VERSION)
	@echo Clojure: $(CLOJURE_VER)
	@echo lein/JVM: $(shell lein version)

local:
	@lein jar && lein install

local-standalone:
	@lein uberjar && lein install

clojars:
	@lein deploy clojars

clean:
	@lein clean
