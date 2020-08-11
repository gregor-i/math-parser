#!/bin/bash

VERSION=$1

echo "releasing $VERSION"

git commit --allow-empty -a -m "released $VERSION"
git tag $VERSION
sbt publish
git push --tags
