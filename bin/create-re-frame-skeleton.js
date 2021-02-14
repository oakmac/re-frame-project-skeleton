#! /usr/bin/env node

// this script intended to be calling via the command line or through npx

const fs = require('fs-plus')
const path = require('path')

const projectName = process.argv[2]

if (!isValidProjectName(projectName)) {
  console.error('Invalid project name:', projectName)
  process.exit(1)
}

const allFiles = fs.listTreeSync('template')

console.log(allFiles)

function isValidProjectName (n) {
  // FIXME: write this
  return true
}
