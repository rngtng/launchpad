#!/bin/sh
VERSION=`ls distribution | awk 'match($0, /[0-9.]+/) { print substr($0,RSTART,RLENGTH) }'`

#git tag -d "v${VERSION}" && git push origin :refs/tags/v${VERSION}

git checkout gh-pages && \
rm -rf download reference examples && \
mv distribution/launchpad*/* . && \
git add -A && \
git commit -m "updated webpage to version ${VERSION}" && \
git push origin && \
git tag -a "v${VERSION}" -m "updated to version ${VERSION}" && \
git push --tags origin && \
rm -rf download reference examples && \
git checkout master