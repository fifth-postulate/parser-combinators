.PHONY: all clean

TARGET=parser-combinators.docx

all: ${TARGET}
	@echo "Done"

%.docx: %.md
	pandoc --highlight-style tango -f markdown -t docx -o $@ $<

clean:
	rm -f ${TARGET}
