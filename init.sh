C=`pwd`
P=/home/jvanek/Desktop/git/pandemic

pushd $P

git  log | grep "^commit" | wc -l
commits=`git  log | grep "^commit"`
last=`git  log | grep "^commit" | tail  -n 1 | sed "s/.* //"`
patches=`git format-patch  8271f06eabc6f50a156781712d255445982d0f05`
for patch in $patches ; do
  sed "s/From: .*/From: \"judovana\" judovana@email.cz/" -i $patch
done
popd

for patch in $patches ; do
 git  am --signoff $P/$patch
done 	
