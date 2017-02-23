JAVA = java -jar AlboC.jar

answers:	easter.res gcd.res mini.res next.res opers.res \
		pointers.res primes.res strings.res

easter.res:	easter
	./easter >easter.res
easter:		easter.alboc
	$(JAVA) easter.alboc

gcd.res:	gcd
	./gcd <gcd.input >gcd.res
gcd:		gcd.alboc
	$(JAVA) gcd.alboc

mini.res:	mini
	./mini >mini.res
mini:		mini.alboc
	$(JAVA) mini.alboc

next.res:	next
	./next <next.input >next.res
next:		next.alboc
	$(JAVA) next.alboc

opers.res:	opers
	./opers >opers.res
opers:		opers.alboc
	$(JAVA) opers.alboc

primes.res:	primes
	./primes >primes.res
primes:		primes.alboc
	$(JAVA) primes.alboc

pointers.res:	pointers
	./pointers >pointers.res
pointers:	pointers.alboc
	$(JAVA) pointers.alboc

strings.res:	strings
	./strings <strings.input >strings.res
strings:	strings.alboc
	$(JAVA) strings.alboc

clean:
	rm -f *.log *~

veryclean:	clean
	rm -f *.res *.s