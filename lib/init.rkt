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