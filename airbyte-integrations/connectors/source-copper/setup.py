#
# Copyright (c) 2023 Airbyte, Inc., all rights reserved.
#


from setuptools import find_packages, setup

MAIN_REQUIREMENTS = ["airbyte-cdk"]

TEST_REQUIREMENTS = ["pytest~=6.2", "pytest-mock~=3.6.1"]

setup(
    entry_points={
        "console_scripts": [
            "source-copper=source_copper.run:run",
        ],
    },    name="source_copper",
    description="Source implementation for Copper.",
    author="Airbyte",
    author_email="contact@airbyte.io",
    packages=find_packages(),
    install_requires=MAIN_REQUIREMENTS,
    package_data={"": ["*.json", "*.yaml", "schemas/*.json", "schemas/shared/*.json"]},
    extras_require={
        "tests": TEST_REQUIREMENTS,
    },
)
