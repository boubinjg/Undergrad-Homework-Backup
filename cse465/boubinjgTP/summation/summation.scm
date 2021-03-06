;;; sum 
(define empty? '())
(define (sumList lst)
	(cond 
		((null? lst) 0)
		((list? lst) (+ (car lst) (sumList(cdr lst))))
		(else 0)
	)
)
(define (check lst num)
	(cond 
		((NULL? lst) #f)
		((equal? lst '(())) #f) 
		((List? lst) (if (= (sumList (car lst)) num)
			#t
			(check (cdr lst) num)
		))
		(else #f)
	)
)
(define (pset set)
  (if (null? set)
      '(())
      (let ((rest (pset (cdr set))))
        (append (map (lambda (element) (cons (car set) element))
                     rest)
                rest))))

(define (sum lst num)
	(cond 
		((NULL? lst) #f)
		((LIST? lst) (check (pset lst) num))
		(ELSE #f)
	)	
)
