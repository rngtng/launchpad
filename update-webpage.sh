#!/bin/sh

rm -rf reference
git checkout gh-pages
rm -rf download
rm -rf examples
rm -rf reference
mv distribution/web/* .
git commit -a -m "updated webpage to latest version"
git push origin
git checkout master