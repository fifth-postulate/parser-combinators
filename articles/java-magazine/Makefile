.PHONY: all clean

TARGET=parser-combinators.docx

all: ${TARGET}
	@echo "Done"

${TARGET}: parser-combinators.md bio.md
	cat $^ | pandoc --highlight-style tango -f markdown -t docx -o $@

clean:
	rm -f ${TARGET}
