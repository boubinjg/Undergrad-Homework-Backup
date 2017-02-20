; zipcodes.scm contains all the US zipcodes.
; You should not modify this file. Your code
; should work for other instances of this file.
(load "zipcodes.scm")

; Helper function
(define (mydisplay value)
	(display value)
	(newline)
	#t
)

; Returns the roots of the quadratic formula, given
; ax^2+bx+c=0. Return only real roots. The list will
; have 0, 1, or two roots
(define (quadratic a b c)
	(cond
		((= a 0) '())
		((<(* b b) (* 4 a c)) '())
		((=(* b b) (* 4 a c)) list (/ b (* 2 a)))
		(else (list 
			(/ (- b (sqrt ( - (* b b) (* 4 a c) )) ) (* 2 a))
			(/ (+ b (sqrt ( - (* b b) (* 4 a c) )) ) (* 2 a))
		))
	)
)

(mydisplay (quadratic 1 0 0))
(mydisplay (quadratic 0 1 0))
(mydisplay (quadratic 3 4 2))
; Return a list with only the negatives items
(define (negatives lst)
	(cond
		((NULL? lst) lst)
		((< (car lst) 0) (cons (car lst) (negatives(cdr lst))))
		(else (negatives(cdr lst)))
	)
)

(mydisplay (negatives '(-1 1 2 3 4 -4 5)))
; Returns true if the two lists have identical structure.
; (struct '(a b c (c a b)) '(1 2 3 (a b c))) -> #t
; (struct '(a b c (c a b) a) '(1 2 3 (a b c) 0)) -> #F
(define (struct lst1 lst2)
	(cond
		( (and (null? lst1) (null? lst2)) #t)
		( (or (null? lst1) (null? lst2)) #f)
		( (and (list? (car lst1)) (list? (car lst2))) 
			(and (struct (car lst1) (car lst2))
			     (struct (cdr lst1) (cdr lst2))))
		( (not (or (list? (car lst1)) (list? (car lst2))))
			(struct (cdr lst1) (cdr lst2)) 
		)
		(else #f)
	)
)
(mydisplay (struct '(a b c (c a b)) '(1 2 3 (a b c))))
(mydisplay (struct '(a b c (c a b)) '(1 2 3 (a b c) 0)))
; Returns a list of two numeric values. The first is the smallest
; in the list and the second is the largest in the list. 
; lst -- flat, contains numeric values, and length is >= 1.
(define (maxelt lst)
	(if (= (length lst) 1) (car lst) (max (car lst) (maxelt (cdr lst))))
)
(define (minelt lst)
	(if (= (length lst) 1) (car lst) (min (car lst) (minelt (cdr lst))))
)
(define (minAndMax lst)
	(list (maxelt lst) (minelt lst))
)
(mydisplay (minAndMax '(1 2 -3 4 2)))
(mydisplay (minAndMax '(1)))
; Returns a list identical to the first, except all nested lists
; are removed:
; (flatten '(a b c)) -> (a b c)
; (flatten '(a (a a) a) -> (a a a a)
; (flatten '((a b) (c (d) e) f) -> (a b c d e f)
;
(define (flatten lst)
	(cond 
		((null? lst) '())
		((list? (car lst)) (append (flatten (car lst)) (flatten (cdr lst))))
		(else (cons (car lst) (flatten(cdr lst))))
	)
)
(mydisplay (flatten '(a b c)))
(mydisplay (flatten '(a (a a) a)))
(mydisplay (flatten '((a b) (c (d) e) f)))
; The paramters are two lists. The result should contain the cross product
; between the two lists: 
; The inputs '(1 2) and '(a b c) should return a single list:
; ((1 a) (1 b) (1 c) (2 a) (2 b) (2 c))
; lst1 & lst2 -- two flat lists with same length.
(define (crosspairs con lst)
	(cond
		((null? lst) '())
		(else ( cons (list con (car lst)) (crosspairs con (cdr lst))))
	)
)
(define (crossproduct lst1 lst2)
	(cond 
		((null? lst1) '())
		(else (append (crosspairs (car lst1) lst2) (crossproduct (cdr lst1) lst2)))	
	)
)

(mydisplay (crossproduct '(1 2) '(a b c)))
; Returns all the latitude and longitude of particular zip code.
; Returns the first lat/lon, if multiple entries have same zip code.
; zipcode -- 5 digit integer
; zips -- the zipcode DB
(define (getLatLon zipcode zips)
	(if ( = (caar zips) zipcode) (list (cadr(cdddar zips)) (caddr(cdddar zips))) (getLatLon zipcode (cdr zips)))
)
(mydisplay (getLatLon 45056 zipcodes))
; Returns a list of all the place names common to two states.
; placeName -- is the text corresponding to the name of the place
; zips -- the zipcode DB
(define (getPlaceList zips state)
	(cond 
		((null? zips) '())
		((string=? (caddar zips) state) 
			(cons (cadar zips) (getPlaceList (cdr zips) state)))
		(else (getPlaceList (cdr zips) state))
	)
	
)
(define (isInList lst item)
	(cond
		((null? lst) #f)
		((string=? (car lst) item) #t)
		(else (isInList (cdr lst) item))
	)
)
(define (isListinList lst1 lst2)
	(cond
		((null? lst1) '())
		((isInList lst2 (car lst1)) (cons (car lst1) (isListinList (cdr lst1) lst2)))
		((not (isInList lst2 (car lst1))) (isListinList (cdr lst1) lst2))
		(else '())
	)
)
(define (getCommonPlaces state1 state2 zips)
	(removedupes (isListinList (getPlaceList zips state1)(getPlaceList zips state2)))
)
(define (removedupes ls)
	(let loop ((ls ls) (seen '()))
 	    (cond
       		((null? ls) '())
       		((member (car ls) seen) (loop (cdr ls) seen))
       		(else (cons (car ls) (loop (cdr ls) (cons (car ls) seen))))))
)
(mydisplay (getCommonPlaces "OH" "MI" zipcodes))
; Returns a list of all the place names common to a set of states.
; states -- is list of state names
; zips -- the zipcode DB

(define (getCommonPlaces2 states zips)
	'("Oxford" "Franklin")
)

;(mydisplay (getCommonPlaces2 '("OH" "MI" "PA") zipcodes))

; Returns the number of zipcode entries for a particular state.
; If a zipcode appears multiple times in zipcodes.scm, count one
; for each occurance.
; state -- state
; zips -- zipcode DB
(define (getlength lst count) 
	(if (null? lst) count (getlength (cdr lst) (+ 1 count)))
)

(define (zipCount state zips)
	(getlength (getPlaceList zips state) 0)
)

(mydisplay (zipCount "OH" zipcodes))
;(mydisplay (getPlaceList zipcodes "OH"))
;(mydisplay (getPlaceList zipcodes "OH"))
; Returns the distance between two zip codes.
; Use lat/lon. Do some research to compute this.
; zip1 & zip2 -- the two zip codes in question.
; zips -- zipcode DB
(define (getDistanceBetweenZipCodes zip1 zip2 zips)
	0
)

;(mydisplay (getDistanceBetweenZipCodes 45056 48122 zipcodes))

; Some sample predicates
(define (POS? x) (> x 0))
(define (NEG? x) (> x 0))
(define (LARGE? x) (>= (abs x) 10))
(define (SMALL? x) (NOT (LARGE? x)))

; Returns a list of items that satisfy a set of predicates.
; For example (filterList '(1 2 3 4 100) '(EVEN?)) should return the even numbers (2 4 100)
; (filterList '(1 2 3 4 100) '(EVEN? SMALL?)) should return (2 4)
; lst -- flat list of items
; filters -- list of predicates to apply to the individual elements
(define (applypred pred lst)
	(cond 
		((null? lst) '())
		((pred (car lst)) (cons (car lst) (applypred pred (cdr lst))))
		(else (applypred pred (cdr lst)))
	)
)
(define (filterList lst filters)
	(cond 
		((NULL? filters) lst)
		((equal? (car filters) 'POS?) (filterList (applypred pos? lst) (cdr filters)))
		((equal? (car filters) 'NEG?) (filterList (applypred neg? lst) (cdr filters)))
		((equal? (car filters) 'LARGE?) (filterList (applypred large? lst)(cdr filters)))
		((equal? (car filters) 'SMALL?) (filterList (applypred small? lst)(cdr filters)))
		((equal? (car filters) 'EVEN?) (filterList (applypred even? lst) (cdr filters)))
		((equal? (car filters) 'ODD? ) (filterList (applypred odd? lst) (cdr filters)))
		(else '())
	)
)
(mydisplay '(100))
(mydisplay (filterList '(1 2 3 11 22 33 -1 -2 -3 -11 -22 -33) '(POS?)))
(mydisplay (filterList '(1 2 3 11 22 33 -1 -2 -3 -11 -22 -33) '(POS? EVEN?)))
(mydisplay (filterList '(1 2 3 11 22 33 -1 -2 -3 -11 -22 -33) '(POS? EVEN? LARGE?)))

; include the following line when on lnx01
,exit
