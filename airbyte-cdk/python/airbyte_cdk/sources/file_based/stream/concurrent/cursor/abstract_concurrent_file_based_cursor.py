#
# Copyright (c) 2023 Airbyte, Inc., all rights reserved.
#

import logging
from abc import ABC, abstractmethod
from datetime import datetime
from typing import TYPE_CHECKING, Any, Dict, List, Iterable, MutableMapping

from airbyte_cdk.sources.file_based.remote_file import RemoteFile
from airbyte_cdk.sources.file_based.stream.cursor import AbstractFileBasedCursor
from airbyte_cdk.sources.file_based.types import StreamState
from airbyte_cdk.sources.streams.concurrent.cursor import Cursor
from airbyte_cdk.sources.streams.concurrent.partitions.record import Record

if TYPE_CHECKING:
    from airbyte_cdk.sources.file_based.stream.concurrent.adapters import FileBasedStreamPartition


class AbstractConcurrentFileBasedCursor(Cursor, AbstractFileBasedCursor, ABC):
    @property
    @abstractmethod
    def state(self) -> MutableMapping[str, Any]:
        ...

    @abstractmethod
    def observe(self, record: Record) -> None:
        ...

    @abstractmethod
    def close_partition(self, partition: "FileBasedStreamPartition") -> None:
        ...

    @abstractmethod
    def set_pending_partitions(self, partitions: List["FileBasedStreamPartition"]) -> None:
        ...

    @abstractmethod
    def add_file(self, file: RemoteFile) -> None:
        ...

    @abstractmethod
    def get_files_to_sync(self, all_files: Iterable[RemoteFile], logger: logging.Logger) -> Iterable[RemoteFile]:
        ...

    @abstractmethod
    def get_state(self) -> MutableMapping[str, Any]:
        ...

    @abstractmethod
    def set_initial_state(self, value: StreamState) -> None:
        ...

    @abstractmethod
    def get_start_time(self) -> datetime:
        ...

    @abstractmethod
    def emit_state_message(self) -> None:
        ...
