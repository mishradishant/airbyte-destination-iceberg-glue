#
# Copyright (c) 2023 Airbyte, Inc., all rights reserved.
#


import sys

from airbyte_cdk.entrypoint import launch
from source_datadog import SourceDatadog

def run():
    source = SourceDatadog()
    launch(source, sys.argv[1:])
