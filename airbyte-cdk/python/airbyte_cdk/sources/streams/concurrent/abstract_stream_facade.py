
from abc import ABC, abstractmethod
from typing import Generic, TypeVar

from airbyte_cdk.sources.streams import Stream


StreamType = TypeVar('StreamType')


class AbstractStreamFacade(Generic[StreamType], ABC):
    @abstractmethod
    def get_underlying_stream(self) -> StreamType:
        """
        Return the underlying stream facade object.
        """
        ...
