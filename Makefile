.PHONY: clean

ARCHIVE=workshop-material.tar.gz
MATERIAL_DIR=parser-combinator-workshop

${ARCHIVE}: ${MATERIAL_DIR} 
	tar cvfz $@ $<

${MATERIAL_DIR}:
	mkdir $@
	echo 'Hello, World!' > $@/greeting.txt

clean:
	rm -rf ${ARCHIVE} ${MATERIAL_DIR}