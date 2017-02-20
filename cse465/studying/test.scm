(define (double lst)
	(cond 
		((NULL? lst) '())
		((else) (cons (* 2 (car lst)) (double (cdr lst))))
	)
)

