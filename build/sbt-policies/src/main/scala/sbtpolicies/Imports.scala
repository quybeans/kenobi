// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies

import sbtpolicies.dependency.DependencyImports
import sbtpolicies.minikube.MinikubeImports
import sbtpolicies.path.PathImports

private[sbtpolicies] trait Imports
  extends DependencyImports
    with PathImports
    with MinikubeImports
