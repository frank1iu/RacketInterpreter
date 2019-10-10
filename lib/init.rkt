;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname init) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))

(define (<= a b)
  (not (> a b)))

(define (>= a b)
  (not (< a b)))

(define (abs x)
  (if (> (- 0 x) x)
      (- 0 x)
      x))

(define (zero? x)
  (= x 0))

(define (max a b)
  (if (> a b)
      a
      b))

(define (min a b)
  (if (< a b)
      a
      b))

(define (negative? x)
  (< x 0))

(define (positive? x)
  (> x 0))

(define (sqr x)
  (* x x))

(define (mod a b)
  (if (< a b)
      a
      (if (< a (* 2 b))
          (- a b)
          (mod (max (- a b) b)
               (min (- a b) b)))))

(define (gcd a b)
  (if (= b 0)
      (abs a)
      (gcd (abs b)
           (mod (abs a) (abs b)))))