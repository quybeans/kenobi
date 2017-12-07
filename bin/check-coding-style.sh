#!/bin/bash

SCALASTYLE_VERSION="1.0.0"

base_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

scalastyle_filename="scalastyle_2.12-${SCALASTYLE_VERSION}-batch.jar"
scalastyle_url="https://oss.sonatype.org/content/repositories/releases/org/scalastyle/scalastyle_2.12/${SCALASTYLE_VERSION}/${scalastyle_filename}"
scalastyle_path="${base_dir}/../tools/${scalastyle_filename}"

function download_if_not_exists {
  if [ ! -f "$1" ]; then
    if [ -n "$3" ]; then
      echo "Downloading $3..."
    fi
    mkdir -p $(dirname "$1")
    wget -q --show-progress -O "$1" "$2"
  fi
}

function get_directories() {
  echo "${base_dir}/../app"
  echo "${base_dir}/../build"
  echo "${base_dir}/../modules"
  echo "${base_dir}/../project"
}

function get_files() {
  for dir in $(get_directories); do
    find "${dir}" \
      -path "${dir}/target" -prune -o \
      -path "${dir}/**/target" -prune -o \
      -type f \
      -name "*.scala" \
      -print
  done
}

download_if_not_exists \
  "${scalastyle_path}" \
  "${scalastyle_url}" \
  "Scalastyle jar file"

echo "Checking coding style..."

get_files | xargs java \
  -Xmx2G \
  -jar "${scalastyle_path}" \
  -c "${base_dir}/../scalastyle-config.xml"
