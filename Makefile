ARCHIVE=workshop-material.tar.gz
MATERIAL_DIR=parser-combinator-workshop
SUB_DIRECTORIES=workshop
CLEAN_TARGETS=$(addsuffix clean,$(SUB_DIRECTORIES))

.PHONY: all clean ${SUB_DIRECTORIES} ${CLEAN_TARGETS}

all: ${ARCHIVE}
${ARCHIVE}: ${MATERIAL_DIR} 
	tar cvfz $@ $<

${MATERIAL_DIR}: ${SUB_DIRECTORIES}
	mkdir -p $@
	cp -rf resources/material/* $@/
	cp -rf workshop/guide/book $@/guide
	mkdir -p $@/example
	cp -rf workshop/example/* $@/example

${SUB_DIRECTORIES}:
	${MAKE} -C $@

clean: ${CLEAN_TARGETS}
	rm -rf ${ARCHIVE} ${MATERIAL_DIR}

%clean: %
	${MAKE} -C $< clean